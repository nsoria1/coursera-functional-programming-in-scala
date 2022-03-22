package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s1001 = singletonSet(1001)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersect contains one element") {
    new TestSets {
      val s = intersect(s1, s1)
      assert(contains(s, 1), "intersect 1")
      assert(!contains(s, 2), "intersect 2")
      assert(!contains(s, 3), "intersect 3")
    }
  }

  test("diff contains all elements") {
    new TestSets {
      val s = diff(union(s1,s2), s2)
      assert(contains(s, 1), "diff 1")
      assert(!contains(s, 2), "diff 2")
      assert(!contains(s, 3), "diff 3")
    }
  }

  test("filter contains all elements") {
    new TestSets {
      val s = filter(union(s1,s2),(x: Int) => x % 2 == 0)
      assert(!contains(s, 1), "filter 1")
      assert(contains(s, 2), "filter 2")
      assert(!contains(s, 3), "filter 3")
    }
  }

  test("forall works") {
    new TestSets {
      val s = union(union(s1, s2), union(s3, s1001))

      assert(forall(s, (x : Int) => (x < 4 && x > 0)))
      assert(!forall(s, (_ < 2)))
    }
  }

  test("exists works") {
    new TestSets {
      val s = union(union(s1, s2), union(s3, s1001))

      assert(exists(s, (_ < 4)))
      assert(exists(s, (_ < 2)))
      assert(!exists(s, (_ < 0)))
    }
  }

  test("map works") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      val mapped = map(s, (_ * 2))

      assert( contains(mapped, 2), "map 2" )
      assert( contains(mapped, 4), "map 4" )
      assert( contains(mapped, 6), "map 6" )

      assert( !contains(mapped, 3), "map 3" )
    }
  }



  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
