-- prints true, since the default _ENV is set to the global table
assert(_ENV == _G)
assert(type(_ENV) == 'table')

function stuff()
    assert(_ENV == _G)
    assert(type(_ENV) == 'table')
end

stuff()

a = 1

local function f1(t)
    local print, error = print, error -- since we will change the environment, standard functions will not be visible

    local _ENV = t -- change the environment. without the local, this would change the environment for the entire chunk

    local success, msg = pcall(assert, nil)
    if success then
        error(msg)
    end

    assert(getmetatable == nil) -- prints nil, since global variables (including the standard functions) are not in the new env
    assert(a == nil)

    a = 2 -- create a new entry in t, doesn't touch the original "a" global
    b = 3 -- create a new entry in t
    local c = 4 -- should not create an entry in t
end

local t1 = {assert= function(x, msg)
    msg = msg or 'assertion failed!'
    if not x then
        error(msg)
    end
end, pcall=pcall}
f1(t1)

assert(a == 1 and b == nil and c == nil)
assert(t1.a == 2 and t1.b == 3 and t1.c == nil)

local called = false

local function f2()
    local assert = assert
    local _ENV = { f=function() stuff() end, myfunc=function() called = true end, doType=type }

    f()

    assert(doType(myfunc) == 'function')

    myfunc()
end

f2()

assert(called)

return true