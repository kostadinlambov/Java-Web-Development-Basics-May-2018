package org.softuni.database.util;

public class RepositoryActionResultImpl implements RepositoryActionResult{

    private Object result;

    public RepositoryActionResultImpl() {
    }

    @Override
    public Object getResult() {
        return this.result;
    }



    @Override
    public void setResult(Object result) {
        this.result = result;
    }
}
