dateFormat = "yyyy.MM.dd G 'at' HH:mm:ss z"
--
--d1 = tostring(date.now())
--d2 = date.parse("2001.07.04 AD at 12:08:56 PDT", dateFormat)
--d3 = date.parse("2021.06.29 AD at 15:24:56 UTC", dateFormat)
--d4 = date.parse("2021.10.30 AD at 00:00:00 GMT", dateFormat)
--d5 = date.fromtime(date.now():time() + math.random(-200000, 200000) * 1000)
--
--local function printOut(date, tz)
--  tz = tz or 'GMT+5:00'
----  tz = tz or 'PKT'
--
--  print('-----------------')
--  print(date)
--  print(date:format(dateFormat, tz))
--  print(date:format("EEE, MMM d, ''yy", tz))
--  print(date:format("h:mm a", tz))
--  print(date:format("hh 'o''clock' a, zzzz", tz))
--  print(date:format("K:mm a, z", tz))
--  print(date:format("yyyyy.MMMMM.dd GGG hh:mm aaa", tz))
--  print(date:format("EEE, d MMM yyyy HH:mm:ss Z", tz))
--  print(date:format("yyMMddHHmmssZ", tz))
--  print(date:format("yyyy-MM-dd'T'HH:mm:ss.SSSZ", tz))
--  print(date:format("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", tz))
--  print(date:format("YYYY-'W'ww-u", tz))
--end
--
--printOut(date.now())
----printOut(d2, 'PDT')
----printOut(d3, 'UTC')
----printOut(d4, 'GMT')
----printOut(d5)

local d = date.now()

--print(d, 'year')
d:year(d:year() - 2)

--print(d, 'month')
d:month(d:month() - 2)

--print(d, 'day')
d:day(d:day() + 50)

--print(d, 'hour')
d:hour(d:hour() - 2)

--print(d, 'minute')
d:minute(d:minute() - 2)

--print(d, 'second')
d:second(d:second() - 2)

--print(d 'millis')
d:millis(d:millis() - 2)

--print(d)
