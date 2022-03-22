import scala.annotation.tailrec

@tailrec
def nth[T](xs: List[T], n: Int): T = {
  if xs.isEmpty then throw IndexOutOfBoundsException()
  else if n == 0 then xs.head
  else nth(xs.tail, n - 1)
}

nth(List(1, 2, 3, 4), 2)

nth(List(1, 2, 3, 4), 5)