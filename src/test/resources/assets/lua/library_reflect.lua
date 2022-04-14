-- assert that classes that don't exist
-- throw an exception
local found, err = java.class 'does.not.Exist'
assert(not found)
assert(err:find 'class not found')

-- testing Point class
local pointCls = 'com.hk.lua.LuaLibraryReflectTest$Point'
local Point = java.class(pointCls)
assert(Point == Point)
assert(Point == java.class(pointCls))
assert(rawequal(Point, java.class(pointCls)))

assert(Point)
assert(type(Point) == 'class ' .. pointCls .. '*')

-- testing instances
local p
local p1 = Point.new()
assert(p1)
assert(type(p1) == pointCls .. '*')
assert(p1 == p1)
assert(tostring(p1) == 'Point{x=0.0, y=0.0}')

local p2 = Point.new(3, 4)
assert(p2)
assert(type(p2) == pointCls .. '*')
assert(p2 == p2)
assert(p1 ~= p2)
assert(tostring(p2) == 'Point{x=3.0, y=4.0}')

local p3 = Point.new(p2)
assert(p3)
assert(type(p3) == pointCls .. '*')
assert(p2 == p3)
assert(p1 ~= p3)
assert(tostring(p3) == 'Point{x=3.0, y=4.0}')
assert(tostring(p2) == tostring(p3))

-- testing non-static fields
p = Point.new(6, 7)
assert(rawget(p, 'x') == 6)
assert(rawget(p, 'y') == 7)
p = Point.new(p1)
assert(rawget(p, 'x') == 0)
assert(rawget(p, 'y') == 0)
p = Point.new(p2)
assert(rawget(p, 'x') == 3)
assert(rawget(p, 'y') == 4)

rawset(p, 'x', 3)
rawset(p, 'y', 4)
assert(tostring(p) == 'Point{x=3.0, y=4.0}')
rawset(p, 'x', -7)
rawset(p, 'y', -10)
assert(p == Point.new(-7, -10))

-- testing non-static methods
p = Point.new(0, 5).setX(1)
assert(p.getX() == 1 and p.getY() == 5)
assert(p == p.setX(1))
assert(not p.setY(-5))
assert(p.toString() == tostring(p))
assert(math.type(p.hashCode()) == 'integer')
assert(p2.hashCode() == p3.hashCode())
assert(p.getClass() == Point)

assert(p('set', Point)(Point.new(22, 33)) == p)
assert(p.getX() == 22 and p.getY() == 33)
assert(p('set', 'double', 'double')(33, 22) == p)
assert(p.getX() == 33 and p.getY() == 22)

-- testing static methods
assert(Point.distance(3, 0, 0, 4) == 5)
assert(Point.distanceSq(3, 0, 0, 4) == 25)
assert(Point.distance(0, 0, 3, 4) == 5)
local distance = Point('distance', 'double', 'double', 'double', 'double')
assert(type(distance) == 'function')
local v1 = Point.distance(-15, 3, 1, 33)
local v2 = distance(-15, 3, 1, 33)
assert(v1 == 34)
assert(v1 == v2)
assert(not Point('distance', 'int', 'int', 'int', 'int'))

local d0, d1, d2, d3, d4, d5, d6, d7, d8
d0 = true

d1, d2, d3, d4, d5, d6, d7, d8, d0 = Point.numbers(8)
assert(d1 == 8 and d2 == 7 and d3 == 6 and d4 == 5 and d5 == 4 and d6 == 3 and d7 == 2 and d8 == 1 and not d0)
d1, d2, d3, d4, d5, d6, d7, d8 = Point.numbers(7)
assert(d1 == 7 and d2 == 6 and d3 == 5 and d4 == 4 and d5 == 3 and d6 == 2 and d7 == 1 and not d8)
d1, d2, d3, d4, d5, d6, d7 = Point.numbers(6)
assert(d1 == 6 and d2 == 5 and d3 == 4 and d4 == 3 and d5 == 2 and d6 == 1 and not d7)
d1, d2, d3, d4, d5, d6 = Point.numbers(5)
assert(d1 == 5 and d2 == 4 and d3 == 3 and d4 == 2 and d5 == 1 and not d6)
d1, d2, d3, d4, d5 = Point.numbers(4)
assert(d1 == 4 and d2 == 3 and d3 == 2 and d4 == 1 and not d5)
d1, d2, d3, d4 = Point.numbers(3)
assert(d1 == 3 and d2 == 2 and d3 == 1 and not d4)
d1, d2, d3 = Point.numbers(2)
assert(d1 == 2 and d2 == 1 and not d3)
d1, d2 = Point.numbers(1)
assert(d1 == 1 and not d2)

-- testing static fields
local zeroPoint = rawget(Point, 'ZERO')
assert(zeroPoint == p1)
found, err = pcall(rawset, Point, 'ZERO', 0)
assert(not found)
assert(err:find 'ZERO(.*\\s.*)final')

assert(rawget(Point, 'magic') == 4321234)
rawset(Point, 'magic', 1111 * 1111)
assert(rawget(Point, 'magic') == 1234321)

-- more static testing under Math
local Math = java.class 'java.lang.Math'

assert(Math == Math)
assert(Math ~= Point)

assert(rawget(Math, 'PI') == math.pi)

found, err = pcall(rawset, Math, 'PI', 2)
assert(not found)
assert(err:find 'java.lang.Math.PI(.*\\s.*)final')

assert(Math.sqrt(4) == 2.0)
assert(Math.toDegrees(math.pi) == 180)
assert(Math.toRadians(45) == math.pi / 4)
assert(Math.toRadians(Math.toDegrees(math.pi)) == math.pi)
assert(Math.toRadians(Math.toDegrees(math.pi)) == rawget(Math, 'PI'))

function testType(f, vl, ...)
    assert(type(f) == 'function')
    local res = f(...)
    assert(res == vl)
    assert(math.type(res) == math.type(vl))
end
testType(Math.max, 3.0, 2.0, 3.0)
found, err = pcall(testType, Math.max, 3, 2.0, 3.0)
assert(not found)
assert(err:find 'failed')

testType(Math('max', 'int', 'int'), 3, 2, 3)
testType(Math('max', 'double', 'double'), 3.0, 2, 3)

testType(Math.abs, 3, 3)
testType(Math.abs, 3.0, 3.0)
testType(Math.abs, 5, -5)
testType(Math('abs', 'long'), 5, -5)
testType(Math('abs', 'float'), 5.0, -5)

assert(not Math('max', 'java.lang.String'))

return true