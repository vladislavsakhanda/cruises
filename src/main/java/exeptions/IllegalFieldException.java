package exeptions;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class IllegalFieldException extends java.lang.Exception
        implements Iterable<Throwable> {
    public IllegalFieldException(String reason) {
        super(reason);
    }

    public IllegalFieldException() {
        super();
    }

    @Override
    public Iterator<Throwable> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Throwable> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Throwable> spliterator() {
        return Iterable.super.spliterator();
    }
}
