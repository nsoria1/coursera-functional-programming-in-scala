import scala.annotation.tailrec

def factorial(n: Int): Int = {
  @tailrec def factorialAcc(acc: Int, n: Int): Int =
  {
    if (n <= 1) acc
    else factorialAcc(n * acc, n - 1)
  }
  factorialAcc(1, n)
}

// 1, 4 --> fA 4, 3
// 4, 3 --> fA 12, 2
// 12, 2 --> fA 12 * 2, 1
// 1

factorial(4)