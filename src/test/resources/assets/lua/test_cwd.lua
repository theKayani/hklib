assert(type(io.cwd) == 'function')
assert(type(io.cwd()) == 'string')

currentDir = os.getprop("user.dir"):gsub('\\\\', '/')

function iShotTheSheriff(files)
    for _, v in ipairs(files) do
        local f = io.open(v, 'r')

        if f then
            f:close()
        else
            error('file not found: "' .. io.cwd() .. '/' .. v .. '"')
        end
    end

    return true
end

-- iShotTheSheriff
function butNotTheDeputy()
    assert(io.cwd():gsub('\\\\', '/') == currentDir)

    return iShotTheSheriff({ 'pom.xml', 'README.md', 'LICENSE' })
end

-- iShotTheSheriff
function butISwearItWasSelfDefense()
    assert(io.cwd():gsub('\\\\', '/') == currentDir .. '/src/test/resources')

    return iShotTheSheriff({ 'test.txt', 'assets/jinja/stuff.jinja', 'assets/lua/library_date.lua', 'assets/json/dog_map.json', 'assets/lua/test_cwd.lua' })
end

-- iShotTheSheriff
function andTheySayItIsACapitalOffense()
    assert(io.cwd():gsub('\\\\', '/') == currentDir .. '/src/test/resources/assets/lua')

    return iShotTheSheriff({ 'calls.lua', 'library_date.lua', 'lunity.lua', 'test_cwd.lua' })
end