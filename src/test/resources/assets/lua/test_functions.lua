local success, msg

-- -------------------- PREDICATES --------------------
-- DOUBLE PREDICATE
assert(type(doublePredicate) == 'function')
assert(doublePredicate(1) == false)
assert(doublePredicate(1.0) == false)

success, msg = pcall(doublePredicate, "string")
assert(not success)
assert(msg:find 'number expected, got string')

success, msg = pcall(doublePredicate, {})
assert(not success)
assert(msg:find 'number expected, got table')

-- INT PREDICATE
assert(type(intPredicate) == 'function')
assert(intPredicate(1) == true)

success, msg = pcall(intPredicate, "string")
assert(not success)
assert(msg:find 'integer expected, got string')

success, msg = pcall(intPredicate, 1.0)
assert(not success)
assert(msg:find 'integer expected, got float')

-- LONG PREDICATE
assert(type(longPredicate) == 'function')
assert(longPredicate(1) == false)

success, msg = pcall(longPredicate, "string")
assert(not success)
assert(msg:find 'integer expected, got string')

success, msg = pcall(longPredicate, 1.0)
assert(not success)
assert(msg:find 'integer expected, got float')

-- STRING PREDICATE
assert(type(stringPredicate) == 'function')
assert(stringPredicate('string') == true)
assert(stringPredicate 'another string' == true)

success, msg = pcall(stringPredicate, {})
assert(not success)
assert(msg:find 'string expected, got table')

success, msg = pcall(stringPredicate, 1.0)
assert(not success)
assert(msg:find 'string expected, got float')

-- -------------------- CONSUMERS --------------------
-- DOUBLE CONSUMER
assert(type(doubleConsumer) == 'function')
assert(doubleConsumer(1.0) == nil)

success, msg = pcall(doubleConsumer, {})
assert(not success)
assert(msg:find 'number expected, got table')

success, msg = pcall(doubleConsumer, "string")
assert(not success)
assert(msg:find 'number expected, got string')

return true