-- This magic line makes a `test` table available,
-- and makes all the assertions available to your tests
test = require('lunity')()

function test:before()
    -- code here will be run before each test
    -- `self` is available to store information needed for the test
end

function test:after()
    -- run after each test, in case teardown is needed
end

-- Tests can have any name (other than 'before' or 'after')
-- The `self` table is the same scratchpad available to before()
function test:foo()
    assertTrue(42 == 40 + 2)
    assertFalse(42 == 40)
    assertEqual(42, 40 + 2)
    assertNotEqual(42, 40, "These better not be the same!")
    assertTableEquals({ a=42 }, { ["a"]=6*7 })
    -- See below for more assertions available
end

-- You can define helper functions for your tests to call with impunity
local function some_utility()
    return "excellent"
end

-- Tests will be run in alphabetical order of the entire function name
-- However, relying on the order of tests is an anti-pattern; don't do it
function test:bar()
    assertType(some_utility(), "string")
end

local allPassed = test{
    useANSI=false, -- turn off ANSI codes (colors/bold) in the output
    useHTML=false,  -- turn on  HTML markup in the output
    quiet=true,    -- silence print() and io.write() other than Lua during the tests
}

if not allPassed then os.exit(1) end