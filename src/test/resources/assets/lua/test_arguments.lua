io.write(1, 1 + 1, 5 - 1)

io.write('hello', 'world!')

io.write('a boolean', true, 'a false boolean', false)

do
    local tbl = {
        'these', 'are', 'my',
        'values.', 'there', 'are',
        'many', 'like', 'it',
        'but', 'these', 'are',
        'mine',
    }
    io.write(table.unpack(tbl))
end

io.write("double quotes", 'single quotes')

io.write [[ "super string" ]]
io.write [[
    'still super' "string
]]

io.write [[ [ ]]
io.write [[ ] ]]
io.write [[
]
}]]
io.write [[]
oh yea]]

io.write('w parenthesis')
io.write 'wo parenthesis'
io.write { 'in a table' }
io.write()

io.stdout:write('using stdout')
io.stdout:write 'using stdout wo parenthesis'
io.stdout:write { 'using stdout', 'in a table' }
io.stdout:write()

return io.close()