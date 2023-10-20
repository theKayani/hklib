-- completely random numbers that were preloaded into the interpreter
local from_string = require('1767/3350/6427/1525189')
assert(type(from_string) == 'table')
assert(from_string.a == 1)
assert(from_string.b == 2)
assert(from_string.c == 3)

local from_file = require('2312/8616/2516/5097625')
assert(type(from_file) == 'table')
assert(from_file.white)
assert(from_file.red)
assert(from_file.mediumturquoise[3] == 0.8)

local from_library = require('7392/4355/5670/3202062')
assert(type(from_library) == 'table')
assert(from_library.ABC == 123)
assert(from_library.DEF == 456)
assert(from_library.GHI == 789)

local from_factory = require('6203/1920/3565/6150394')
assert(type(from_factory) == 'table')
assert(#from_factory == 5)
for i = 1, 5 do
    assert(from_factory[i] == i * 10)
end

return true