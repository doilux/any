package com.doilux.any
/**
 * This is sample of Any3
 */
class Any3Sample {

    static void main(String[] args) {
        assert Order.notExist().order().getSuccess() instanceof InProgressOrder
        assert Order.inProgress().order().getError() == "order is already in progressed"
        assert Order.completed().order().getError() == "order is already complete"

        assert Order.notExist().complete().getError() == "order is not exist"
        assert Order.inProgress().complete().getSuccess() instanceof CompletedOrder
        assert Order.completed().complete().getError() == "order is already complete"
    }
}

class Order {
    private final Any3<NotExistOrder, InProgressOrder, CompletedOrder> value

    private Order(Any3<NotExistOrder, InProgressOrder, CompletedOrder> value) {
        this.value = value
    }

    static Order notExist() {
        return new Order(Any3.first(new NotExistOrder()))
    }

    static Order inProgress() {
        return new Order(Any3.second(new InProgressOrder()))
    }

    static Order completed() {
        return new Order(Any3.third(new CompletedOrder()))
    }

    Validation<InProgressOrder> order() {
        return value.fold(
                { Validation.valid(it.order()) },
                { Validation.invalid("order is already in progressed") },
                { Validation.invalid("order is already complete") }
        )
    }

    Validation<CompletedOrder> complete() {
        return value.fold(
                { Validation.invalid("order is not exist") },
                { Validation.valid(it.complete()) },
                { Validation.invalid("order is already complete") }
        )
    }
}

class NotExistOrder {
    private InProgressOrder order() {
        return new InProgressOrder()
    }
}

class InProgressOrder {
    private CompletedOrder complete() {
        return new CompletedOrder()
    }
}

class CompletedOrder {}

class Validation<SUCCESS> {
    private final String errMsg
    private final SUCCESS success

    private Validation(String error, SUCCESS success) {
        this.errMsg = error
        this.success = success
    }

    static Validation valid(SUCCESS s) {
        return new Validation(null, s)
    }

    static Validation invalid(String e) {
        return new Validation(e, null)
    }

    String getError() {
        return Optional.ofNullable(errMsg).get()
    }

    SUCCESS getSuccess() {
        return Optional.ofNullable(success).get()
    }
}

