local found, err = java.class 'does.not.Exist'
assert(not found)
assert(err:find 'class not found')

local Point = java.class 'java.awt.Point'
assert(Point == Point)

assert(Point)
assert(type(Point) == 'class java.awt.Point*')

local p1 = Point.new()
assert(p1)
assert(type(p1) == 'java.awt.Point*')
assert(p1 == p1)

local p2 = Point.new(3, 4)
assert(p2)
assert(type(p2) == 'java.awt.Point*')
assert(p2 == p2)
assert(not (p1 == p2))

local p3 = Point.new(p2)
assert(p3)
assert(type(p3) == 'java.awt.Point*')
assert(p2 == p3)
assert(not (p1 == p3))

local Math = java.class 'java.lang.Math'

assert(Math == Math)
assert(not (Math == Point))

assert(rawget(Math, 'PI') == math.pi)

return true