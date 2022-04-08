-- testing closures with 'for' control variable
a = {}
for i=1,10 do
    a[i] = {set = function(x) i=x end, get = function () return i end}
    if i == 3 then break end
end
assert(a[4] == nil)
a[1].set(10)
assert(a[2].get() == 2)
a[2].set('a')
assert(a[3].get() == 3)
assert(a[2].get() == 'a')

a = {}
local t = {"a", "b"}
for i = 1, #t do
    local k = t[i]
    a[i] = {set = function(x, y) i=x; k=y end,
            get = function () return i, k end}
    if i == 2 then break end
end
a[1].set(10, 20)
local r,s = a[2].get()
assert(r == 2 and s == 'b')
r,s = a[1].get()
assert(r == 10 and s == 20)
a[2].set('a', 'b')
r,s = a[2].get()
assert(r == "a" and s == "b")


-- testing closures with 'for' control variable x break
for i=1,3 do
    f = function () return i end
    break
end
assert(f() == 1)

for k = 1, #t do
    local v = t[k]
    f = function () return k, v end
    break
end
assert(({f()})[1] == 1)
assert(({f()})[2] == "a")


-- testing closure x break x return x errors

local b
function f(x)
    local first = 1
    while 1 do
        if x == 3 and not first then return end
        local a = 'xuxu'
        b = function (op, y)
            if op == 'set' then
                a = x+y
            else
                return a
            end
        end
        if x == 1 then do break end
        elseif x == 2 then return
        else if x ~= 3 then error() end
        end
        first = nil
    end
end

for i=1,3 do
    f(i)
    assert(b('get') == 'xuxu')
    b('set', 10); assert(b('get') == 10+i)
    b = nil
end

pcall(f, 4);
assert(b('get') == 'xuxu')
b('set', 10); assert(b('get') == 14)


local w
-- testing multi-level closure
function f(x)
    return function (y)
        return function (z) return w+x+y+z end
    end
end

y = f(10)
w = 1.345
assert(y(20)(30) == 60+w)

return true