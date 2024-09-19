# RTBC
Reactive Text Based Calculator

# Run
## Pre Requisites:
* Java 17+
* Docker (Optional)

## Docker Instructions
### Build
docker build -t ***{tag name}*** -f ***{docker file location}*** .

### Run
docker run -d -p 8080:8080 ***{tag name}***

## Swagger
### <a href="http://localhost:8080/v3/webjars/swagger-ui/index.html">UI</a>
Since Json doesn't handle newline that well, make sure to use '\n'

### Examples
1. 
    * ***Input***: i = 0\nj = ++ i\nx = i++ + 5\ny = 5 + 3 * 10\ni += y
    * ***Output***: i=37, j=1, x=6, y=35
2.  * ***Input***: a =           10\nb = 5\nc = 2\na =      a + b\nb = b * c\nc = a - b\na = a / c\nb = b + 3\nc = c * 2
    * ***Output***: a=3.0, b=13, c=10
3.  * ***Input***: x = 20\ny = 4\nz = 3\nx += y\ny *= z\nz -= 1\nx /= z\ny += x\nz *= 5
    * ***Output***: x=12.0, y=24.0, z=10
4.  * ***Input***: a = 15\nb = 3\nc = 7\na -= b\nb += c\nc *= 2\na *= b\nb /= 2\nc += a
    * ***Output***: a=120, b=5.0, c=134
5.  * ***Input***: i = 5\nj = 10\nk = 3\ni++\n++j\nk--\nj += i\ni += 2\nk += j\nj--
    * ***Output***: i=8, j=16, k=19
6.  * ***Input***: i = 5\nj = 10\nk = 3.5\ni++\n++j\nk--\nj += i\ni += 2\nk += j\nj--
    * ***Output***: i=8, j=16, k=19.5
7.  * ***Input***: i = 5\nj = 10\nk = 3.5\ni++\n++j\nk--\nj += (i * (2 + 3))\ni += ((1 + 2) * (2 + 1))\nk += (j - (i / (3 - 1)))\nj -= ((k + (1 * i)) / 2)
    * ***Output***: i=15, j=15.5, k=36.0
8.  * ***Input***: i = 5\nj = 10\nk = 3.5\nl += (i * 2)\nm = (j + n)\nk += (l - (m / 2))
    * ***Output***: Variable l is not initialized
9.  * ***Input***: i = 5\nj = 10\nk = 3.5\nresult = (i + (j * 2)\nwrong = (k + (j / 2))\ntotal = (i + j)) * k
    * ***Output***: Invalid arithmetic expression, brackets not well formed
10. * ***Input***: i = 5\nj = 10\nk = 3.5\nresult = i --- j\nk = ---i\ntotal = (j --- k) + 2
    * ***Output***: Parsing error expression: ---i, message: Pre operator cannot be applied
11. * ***Input***: i = 5\nj = 10\nk = 3.5\nresult = 5a + j\ntotal = b3 + k\nfinal = (i + 10c) / j
    * ***Output***: Parsing error expression: 5a+j, message: Invalid number definition
12. * ***Input***: i = 5\nj = 10\nk = 3.5\nresult = i ++ j\ntotal = ++j + k\nfinal = i + j++
    * ***Output***: Parsing error expression: i++j, message: No operator after post operator
13. * ***Input***: a = 5\nb = 3\nc = 12\nd = 4\ne = 6\nf = 2\ng = 8\nh = 1\nresult = (((a + b) * (c / d)) - (e - f) + (b * (g + b))) / (f + b) + ((g * c) - (e / (f + f))) * (f - h) + ((a + b) / 2) * ((f - h) + (b * b)) - ((e * 2) + (b * (a - f))) + ((c / 2) + (b * 2)) * (g - h) + (c % b) - (b * 2)
    * ***Output***: result = 202.1, a = 5, b = 3, c = 12, d = 4, e = 6, f = 2, g = 8, h = 1









