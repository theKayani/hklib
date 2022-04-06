-- https://www.tutorialspoint.com/lua/lua_object_oriented.htm

-- Meta class
Shape = {area = 0}

-- Base class method new

function Shape:new (o,side)
    o = o or {}
    setmetatable(o, self)
    self.__index = self
    side = side or 0
    self.area = side*side;
    return o
end

-- Base class method printArea

function Shape:getArea()
    return "Shape", self.area
end

Square = Shape:new()

-- Derived class method new

function Square:new (o,side)
    o = o or Shape:new(o,side)
    setmetatable(o, self)
    self.__index = self
    return o
end

-- Derived class method printArea

function Square:getArea()
    return "Square", self.area
end

Rectangle = Shape:new()

-- Derived class method new

function Rectangle:new (o,length,breadth)
    o = o or Shape:new(o)
    setmetatable(o, self)
    self.__index = self
    self.area = length * breadth
    return o
end

-- Derived class method printArea

function Rectangle:getArea()
    return "Rectangle", self.area
end

for k, v in pairs(_G) do
    print(k, v)
end

-- Creating the objects
local type, area

myshape = Shape:new(nil,10)
type, area = myshape:getArea()
assert(type == 'Shape')
assert(area == 100)

mysquare = Square:new(nil,10)
type, area = mysquare:getArea()
assert(type == 'Square')
assert(area == 100)

myrectangle = Rectangle:new(nil,10,20)
type, area = myrectangle:getArea()
assert(type == 'Rectangle')
assert(area == 200)

return true