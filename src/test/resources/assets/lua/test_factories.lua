local expectedLen, expectedTotal = ...

assert(math.type(expectedLen) == 'integer')

if arr then
    assert(math.type(expectedTotal) == 'integer')
    assert(#arr == expectedLen)
else
    assert(expectedLen > 0)
    assert(expectedTotal == nil)

    arr = {}
    expectedTotal = 0
    for i = 1, expectedLen do
        local v = math.random(10)
        arr[i] = v
        expectedTotal = expectedTotal + v
    end
end

local total = 0
for _, v in ipairs(arr) do
    assert(math.type(v) == 'integer')

    total = total + v
end

assert(total == expectedTotal)

return true