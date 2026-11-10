#!/usr/bin/env python3
"""Exercise real HTTP APIs and S3 bytes. Uses a fresh test account per run."""
import http.cookiejar, urllib.request, urllib.error, json, time, io, zipfile
BASE = 'http://localhost:8080'
class Client:
    def __init__(self):
        self.opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(http.cookiejar.CookieJar()))
    def call(self, method, path, body=None, status=200):
        data = json.dumps(body).encode() if body is not None else None
        req = urllib.request.Request(BASE+path, data=data, method=method, headers={'Content-Type':'application/json'})
        try:
            r = self.opener.open(req, timeout=20)
        except urllib.error.HTTPError as e:
            r = e
        content = r.read()
        assert r.status == status, (method, path, r.status, content)
        return json.loads(content)
def passed(name): print('PASS:', name, flush=True)
u, admin, anon = Client(), Client(), Client()
name = 'check' + str(int(time.time()))
assert anon.call('GET', '/actuator/health')['status'] == 'UP'
anon.call('GET', '/api/files/list', status=401)
u.call('POST', '/api/login', {'username':'demo','password':'wrong'}, status=401)
u.call('POST', '/api/login', {'username':'demo','password':'demo123'})
admin.call('POST', '/api/login', {'username':'admin','password':'admin123'})
passed('health, demo/admin credentials and rejected anonymous access')
u.call('POST', '/api/logout')
r=u.call('POST', '/api/auth/register', {'username':name, 'password':'test123', 'email':name+'@example.test'})
uid=r['user']['id']
u.call('POST', '/api/login', {'username':name,'password':'test123'})
u.call('GET', '/admin/user-list', status=403)
assert 'password' not in json.dumps(admin.call('GET', '/admin/user-list'))
passed('registration, login, admin authorization and hidden password hashes')
root=u.call('POST','/api/folders/create',{'folderName':'Assignment'})['folder']['id']
child=u.call('POST','/api/folders/create',{'folderName':'Documents','parentFolderId':root})['folder']['id']
assert len(u.call('GET',f'/api/folders/{child}/breadcrumb')['breadcrumb']) == 3
r=u.call('PUT',f'/api/folders/{root}/move?targetParentFolderId={child}',status=500)
assert 'descendants' in r['message']
u.call('PUT',f'/api/folders/{child}/rename',{'newName':'Reports'})
u.call('PUT',f'/api/folders/{child}/move')
u.call('PUT',f'/api/folders/{child}/move?targetParentFolderId={root}')
passed('nested folders, breadcrumbs, rename, move and cycle rejection')
files=[]
for filename,data in [('report.txt',b'Final assignment demo\n'*40),('notes.txt',b'Meeting notes\n'*10)]:
    r=u.call('POST','/api/files/upload-url',{'fileName':filename,'fileSize':len(data),'folderId':child})
    urllib.request.urlopen(urllib.request.Request(r['uploadUrl'],data=data,method='PUT',headers={'Content-Type':'text/plain'}),timeout=20).read()
    fid=r['fileId']; files.append((fid,data))
    u.call('POST','/api/files/upload/confirm',{'fileId':fid,'contentType':'text/plain'})
    url=u.call('GET',f'/api/files/{fid}/download')['downloadUrl']
    assert urllib.request.urlopen(url).read()==data
assert u.call('GET','/api/files/storage')['storageUsed']==sum(len(d) for _,d in files)
assert u.call('GET','/api/files/search?query=report')['totalItems']==1
assert u.call('GET','/api/files/search/advanced?fileType=text&minSize=100&maxSize=10000')['totalItems']==2
assert u.call('GET',f'/api/files/list?folderId={child}&size=1')['totalPages']==2
passed('direct S3 uploads, byte-identical downloads, storage accounting, search/filter and pagination')
fid=files[0][0]
u.call('PUT',f'/api/files/{fid}/rename',{'newName':'final-report.txt'})
token=u.call('POST',f'/api/files/{fid}/share')['publicLinkToken']
assert anon.call('GET',f'/api/public/files/{token}')['fileName']=='final-report.txt'
url=anon.call('GET',f'/api/public/files/{token}/download')['downloadUrl']
assert urllib.request.urlopen(url).read()==files[0][1]
u.call('DELETE',f'/api/files/{fid}/share')
anon.call('GET',f'/api/public/files/{token}',status=404)
passed('file rename, anonymous public sharing/download and link revocation')
ids=[f for f,_ in files]
url=u.call('POST','/api/files/bulk-download',{'fileIds':ids})['downloadUrl']
z=zipfile.ZipFile(io.BytesIO(urllib.request.urlopen(url).read()))
assert sorted(z.read(n) for n in z.namelist())==sorted(d for _,d in files)
u.call('POST','/api/files/bulk-move',{'fileIds':ids,'targetFolderId':root})
assert len(u.call('GET',f'/api/files/list?folderId={root}')['files'])==2
u.call('PUT',f'/api/files/{fid}/move?targetFolderId={child}')
passed('ZIP contents, bulk move and single file move')
used=sum(len(d) for _,d in files)
admin.call('PUT',f'/admin/quota/{uid}',{'storageQuotaBytes':used+1})
r=u.call('POST','/api/files/upload-url',{'fileName':'too-big.txt','fileSize':100},status=500)
assert 'quota exceeded' in r['message']
admin.call('POST',f'/admin/permission/{uid}',{'create':False,'read':False,'write':False})
u.call('GET',f'/api/files/{fid}/download',status=403)
r=u.call('POST','/api/files/upload-url',{'fileName':'blocked.txt','fileSize':1},status=500)
assert 'permission' in r['message']
admin.call('POST',f'/admin/permission/{uid}',{'create':True,'read':True,'write':True})
admin.call('PUT',f'/admin/quota/{uid}',{'storageQuotaGb':1})
assert admin.call('GET','/admin/stats')['totalUsers']>=3
passed('live permission updates, enforced quotas and admin statistics')
u.call('DELETE',f'/api/files/{fid}')
u.call('POST','/api/files/bulk-delete',{'fileIds':[files[1][0]]})
# Bulk delete is asynchronous; wait for the result.
for _ in range(40):
    if u.call('GET','/api/files/storage')['storageUsed']==0: break
    time.sleep(.25)
assert u.call('GET','/api/files/storage')['storageUsed']==0
u.call('DELETE',f'/api/folders/{root}?deleteContents=true')
u.call('POST','/api/logout')
u.call('GET','/api/me',status=401)
passed('single/bulk deletion, storage released, recursive folder deletion and logout')
print('ALL API CHECKS PASSED')
