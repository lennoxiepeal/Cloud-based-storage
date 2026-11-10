#!/usr/bin/env python3
"""Record/check demo account's folder metadata and SHA-256 of stored file bytes."""
import hashlib, http.cookiejar, json, pathlib, sys, urllib.request
base = 'http://localhost:8080'
opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(http.cookiejar.CookieJar()))
opener.open(urllib.request.Request(base+'/api/login', data=json.dumps({'username':'demo','password':'demo123'}).encode(), headers={'Content-Type':'application/json'})).read()
def get(path): return json.load(opener.open(base+path, timeout=20))
def flatten(nodes):
    result = []
    for n in nodes:
        result.append({'id':n['id'], 'name':n['name'], 'parentId':n.get('parentId')})
        result.extend(flatten(n.get('children',[])))
    return result
folders = sorted(flatten(get('/api/folders/tree')['tree']), key=lambda f:f['id'])
files=[]
for folder in [None]+[f['id'] for f in folders]:
    query = f'&folderId={folder}' if folder else ''
    for f in get('/api/files/list?size=1000'+query)['files']:
        data = urllib.request.urlopen(get(f"/api/files/{f['id']}/download")['downloadUrl'], timeout=20).read()
        files.append({'id':f['id'], 'name':f['fileName'], 'folder':folder, 'sha256':hashlib.sha256(data).hexdigest()})
snapshot = {'folders':folders, 'files':sorted(files,key=lambda f:f['id'])}
p = pathlib.Path('logs/persistence-snapshot.json')
if sys.argv[1:] == ['record']:
    assert files, 'Upload demo files first'
    p.write_text(json.dumps(snapshot,indent=2))
    print(f"Recorded {len(folders)} folders and {len(files)} file hashes")
else:
    assert snapshot == json.loads(p.read_text()), 'Stored data changed across restart'
    print(f"PASS: all {len(folders)} folders and {len(files)} byte-identical files survived restart")
