-- testing hashing functions
local str = 'Hello World!'
assert(type(hash.funcs) == 'table')

-- these three should always be available

assert(hash.funcs.md5)
assert(type(hash.md5) == 'function')
assert(type(hash.funcs['md5']) == 'function')
assert(hash.funcs['md5'] == hash.md5)
assert(hash.md5(str) == 'ed076287532e86365e841e92bfc50d8c')

if hash.funcs.sha_1 then
    assert(type(hash.sha_1) == 'function')
    assert(type(hash.funcs['sha_1']) == 'function')
    assert(hash.funcs['sha_1'] == hash.sha_1)
    assert(hash.sha_1(str) == '2ef7bde608ce5404e97d5f042f95f89f1c232871')
end

if hash.funcs.sha_256 then
    assert(type(hash.sha_256) == 'function')
    assert(type(hash.funcs['sha_256']) == 'function')
    assert(hash.funcs['sha_256'] == hash.sha_256)
    assert(hash.sha_256(str) == '7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069')
end

if hash.funcs.sha_512 then
    assert(type(hash.sha_512) == 'function')
    assert(hash.funcs['sha_512'] == hash.sha_512)
    assert(hash.sha_512(str) == '861844d6704e8573fec34d967e20bcfef3d424cf48be04e6dc08f2bd58c729743371015ead891cc3cf1c9d34b49264b510751b1ff9e537937bc46b5d6ff4ecc8')
end

for algorithm, hashFunc in pairs(hash.funcs) do
    assert(hashFunc == hash[algorithm])
    assert(hashFunc(str) == hash[algorithm](str))
end

-- testing zip file utility
assert(hash.zip('src/test/resources/assets/tozip', 'src/test/resources/assets/tozip.zip'))
assert(hash.unzip('src/test/resources/assets/tozip.zip', 'src/test/resources/assets/unzipped'))

local data = dofile('src/test/resources/assets/unzipped/data.lua')

assert(data)
assert(data.special == 'la romana')
local n1, n2, n3 = data.numbers()
assert(n1 == 1 and n2 == 2 and n3 == 3)

local txt = [[
<!DOCTYPE html>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <p>Hello World!</p>
    </body>
</html>]]
local f = io.open('src/test/resources/assets/unzipped/html/random.html')
assert(f)
assert(txt == f:read('a'):gsub("\r\n", "\n"))
f:close()

return true