package work.doilux.any

import spock.lang.Specification
import spock.lang.Unroll
import work.doilux.any.Any3

import java.util.function.Consumer
import java.util.function.Function

@Unroll
class Any3Test extends Specification {

    def "when #sut is called #isMethod, you can get #result"(Any3<String, String, String> sut, Function<Any3, Boolean> isMethod, Boolean result) {
        expect:
        isMethod.apply(sut) == result

        where:
        sut                  | isMethod          || result
        Any3.first("input")  | { it.isFirst() }  || true
        Any3.first("input")  | { it.isSecond() } || false
        Any3.first("input")  | { it.isThird() }  || false
        Any3.second("input") | { it.isFirst() }  || false
        Any3.second("input") | { it.isSecond() } || true
        Any3.second("input") | { it.isThird() }  || false
        Any3.third("input")  | { it.isFirst() }  || false
        Any3.third("input")  | { it.isSecond() } || false
        Any3.third("input")  | { it.isThird() }  || true
    }

    def "when #sut is called `bimap`, you can get #result"(Any3<String, String, String> sut, Any3 result) {
        expect:
        final Function<String, Integer> f1 = { 1 }
        final Function<String, Integer> f2 = { 2 }
        final Function<String, Integer> f3 = { 3 }
        sut.bimap(f1, f2, f3) == result

        where:
        sut                  || result
        Any3.first("input")  || Any3.first(1)
        Any3.second("input") || Any3.second(2)
        Any3.third("input")  || Any3.third(3)
    }

    def "when #sut is called `peek`, you can get #sut and #msg as result of input"(Any3<String, String, String> sut, Integer msg) {
        expect:
        def m = [] as List
        final Consumer<String> c1 = { m.add(1) }
        final Consumer<String> c2 = { m.add(2) }
        final Consumer<String> c3 = { m.add(3) }
        sut.peek(c1, c2, c3) == sut
        m.get(0) == msg

        where:
        sut                   || msg
        Any3.first("first")   || 1
        Any3.second("second") || 2
        Any3.third("third")   || 3
    }

    def "when #sut is called `forEach`, you can get #sut and #msg as result of input"(Any3<String, String, String> sut, Integer msg) {
        expect:
        def m = [] as List
        final Consumer<String> c1 = { m.add(1) }
        final Consumer<String> c2 = { m.add(2) }
        final Consumer<String> c3 = { m.add(3) }
        sut.forEach(c1, c2, c3)
        m.get(0) == msg

        where:
        sut                   || msg
        Any3.first("first")   || 1
        Any3.second("second") || 2
        Any3.third("third")   || 3
    }

    def "when #sut is called `fold`, you can get #result"(Any3<String, String, String> sut, Integer result) {
        expect:
        final Function<String, Integer> f1 = { 1 }
        final Function<String, Integer> f2 = { 2 }
        final Function<String, Integer> f3 = { 3 }
        sut.fold(f1, f2, f3) == result

        where:
        sut                  || result
        Any3.first("input")  || 1
        Any3.second("input") || 2
        Any3.third("input")  || 3
    }
}
