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

-- Creating the objects
local type, area

myshape = Shape:new(nil,10)
type, area = myshape:getArea()
assert(type == 'Shape')
assert(area == 100)
assert(getmetatable(myshape) == Shape)

myshape = Shape:new(nil,1)
type, area = myshape:getArea()
assert(type == 'Shape')
assert(area == 1)
assert(getmetatable(myshape) == Shape)

myshape = Shape:new(nil,5)
type, area = myshape:getArea()
assert(type == 'Shape')
assert(area == 25)
assert(getmetatable(myshape) == Shape)

mysquare = Square:new(nil,10)
type, area = mysquare:getArea()
assert(type == 'Square')
assert(area == 100)
assert(getmetatable(mysquare) == Square)

mysquare = Square:new(nil,100)
type, area = mysquare:getArea()
assert(type == 'Square')
assert(area == 10000)
assert(getmetatable(mysquare) == Square)

mysquare = Square:new(nil,3)
type, area = mysquare:getArea()
assert(type == 'Square')
assert(area == 9)
assert(getmetatable(mysquare) == Square)

myrectangle = Rectangle:new(nil,10,20)
type, area = myrectangle:getArea()
assert(type == 'Rectangle')
assert(area == 200)
assert(getmetatable(myrectangle) == Rectangle)

myrectangle = Rectangle:new(nil,2,10)
type, area = myrectangle:getArea()
assert(type == 'Rectangle')
assert(area == 20)
assert(getmetatable(myrectangle) == Rectangle)

myrectangle = Rectangle:new(nil,5,100)
type, area = myrectangle:getArea()
assert(type == 'Rectangle')
assert(area == 500)
assert(getmetatable(myrectangle) == Rectangle)

myrectangle = Rectangle:new(nil,50,50)
type, area = myrectangle:getArea()
assert(type == 'Rectangle')
assert(area == 2500)
assert(getmetatable(myrectangle) == Rectangle)

return true