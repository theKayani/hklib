-- These tests are from the
-- Lua 5.3 files.lua test suite

assert(io.read(0) == "")   -- not eof
assert(io.read(5, 'l') == '"álo"')
assert(io.read(0) == "")
assert(io.read() == "second line")
--local x = io.input():seek()
assert(io.read('L') == "third line \n")
--assert(io.input():seek("set", x))
--assert(io.read('L') == "third line \n")
assert(io.read(1) == "ç")
assert(io.read(string.len"fourth_line") == "fourth_line")
--assert(io.input():seek("cur", -string.len"fourth_line"))
--assert(io.read() == "fourth_line")
assert(io.read() == "")  -- empty line
assert(io.read('n') == 3450)
assert(io.read(1) == '\n')
assert(io.read(0) == nil)  -- end of file
assert(io.read(1) == nil)  -- end of file
assert(io.read(30000) == nil)  -- end of file
assert(({io.read(1)})[2] == nil)
assert(io.read() == nil)  -- end of file
assert(({io.read()})[2] == nil)
assert(io.read('n') == nil)  -- end of file
assert(({io.read('n')})[2] == nil)
assert(io.read('a') == '')  -- end of file (OK for 'a')
assert(io.read('a') == '')  -- end of file (OK for 'a')

return true