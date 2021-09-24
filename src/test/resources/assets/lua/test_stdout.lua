function testClose()
    local output = io.output()
    assert(io.type(output) == 'file')

    output:close()

    assert(io.type(output) == 'closed file')

    assert(not io.stdout:close())

    success, err_msg = pcall(io.close, output)

    assert(not success)
    assert(err_msg)

    return true
end

function testFlushBegin()
    io.write 'hello world!'

    return true
end

function testFlushEnd()
    io.flush()

    return true
end