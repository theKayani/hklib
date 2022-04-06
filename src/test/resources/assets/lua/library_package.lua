local homedir = os.getprop('user.home')
assert(package.config == io.sep .. '\n;\n?\n!\n-\n')
assert(package.path == './?.lua;./?/init.lua;./src/test/resources/assets/lua/?.lua;./src/test/resources/assets/lua/?/init.lua;' .. homedir .. '/?.lua;' .. homedir .. '/?/init.lua')

local res, tried

res, tried = package.searchpath('library_package', package.path)
assert(res)
assert(not tried)

res, tried = package.searchpath('does_not_exist', package.path)
assert(not res)
assert(tried)

package.loaded['stuff'] = 2
assert(not package.loaded['stuff'])
rawset(package.loaded, 'stuff', 2)
assert(not package.loaded['stuff'])

Vector = require('vector')

local triples = {
    { 3, 4, 5 }, { 6, 8,10 }, { 5, 12, 13 },
    { 9, 12, 15 }, { 8, 15, 17 }, { 12, 16, 20 },
    { 15, 20, 25 }, { 7, 24, 25 }, { 10, 24, 26 },
    { 20, 21, 29 }, { 18, 24, 30 }, { 16, 30, 34 }
}

for _, triple in ipairs(triples) do
    local v = Vector.new(triple[1], triple[2])
    assert(tostring(v) == '(' .. triple[1] .. ', ' .. triple[2] .. ')')
    assert(v:lenSq() == triple[3] * triple[3])
    assert(v:len() == triple[3])
end

local success, msg = pcall(require, 'does_not_exist')

assert(not success)
assert(type(msg) == 'string')