-- These tests are (almost) verbatim from
-- the Lua 5.3 files.lua test suite

function testClose()
    local input = io.input()
    assert(io.type(input) == 'file')

    input:close()

    assert(io.type(input) == 'closed file')

    assert(not io.stdin:close())

    success, err_msg = pcall(io.close, input)

    assert(not success)
    assert(err_msg)

    return true
end

function testRead()
    assert(io.read(0) == "")   -- not eof
    assert(io.read(5, 'l') == '"\225lo"')
    assert(io.read(0) == "")
    assert(io.read() == "second line")
    --local x = io.input():seek()
    assert(io.read() == "third line ")
    --assert(io.input():seek("set", x))
    --assert(io.read('L') == "third line \n")
    assert(io.read(1) == "\231")
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
end

function testReadLineFormatIO()
    assert(io.read"L" == "\n")
    assert(io.read"L" == "\n")
    assert(io.read"L" == "line\n")
    assert(io.read"L" == "other")
    assert(io.read"L" == nil)

    return true
end

function testReadLineFormatInput()
    local s = ""
    for l in io.input():lines("L") do s = s .. l end

    assert(s == "\n\nline\nother")

    return true
end

function testReadLowLineFormat()
    local s = ""
    for l in io.input():lines("l") do s = s .. l end

    assert(s == "lineother")

    return true
end

function testReadMultipleFormats()
    local _,a,b,c,d,e,h,__ = io.read(1, 'n', 'n', 'l', 'l', 'l', 'a', 10)
    assert(io.close(io.input()))
    assert(_ == ' ' and __ == nil)
    assert(type(a) == 'number' and a==123.4 and b==-56e-2)
    assert(d=='second line' and e=='third line')
    assert(h==[[

and the rest of the file
]])

    return true
end

function testLinesCount()
    local f = io.lines()
    local i = 0

    assert(f)
    while f() do
        i = i + 1
    end

    return i
end

function testLinesNumbers()
    local i = 1

    for n in io.input():lines("n") do
        assert(n == i)
        i = i + 1
    end

    return i - 1
end

function testLinesMultipleFormats1()
    local runs = 0
    for a,b in io.input():lines(1, 1) do
        if a == "\n" then assert(b == nil)
        else assert(tonumber(a) == tonumber(b) - 1)
        end
        runs = runs + 1
    end
    assert(runs == 6)

    return true
end

function testLinesMultipleFormats2()
    local runs = 0
    for a,b,c in io.input():lines(1, 2, "a") do
        assert(a == "0" and b == "12" and c == "3456789\n")
        runs = runs + 1
    end
    assert(runs == 1)

    return true
end

function testLinesMultipleFormats3()
    local runs = 0
    for a,b,c in io.input():lines("a", 0, 1) do
        if a == "" then break end
        assert(a == "0123456789\n" and b == nil and c == nil)
        runs = runs + 1
    end
    assert(runs == 1)

    return true
end

function testLinesMultipleNumberFormats()
    local runs = 0
    for a, b in io.input():lines("n", "n") do
        if a == 40 then assert(b == nil)
        else assert(a == b - 10)
        end
        runs = runs + 1
    end
    assert(runs == 3)

    return true
end