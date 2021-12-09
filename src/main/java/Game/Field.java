package Game;

public class Field {

    private final int row;
    private final int column;

    public Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass())return false;
        Field other = (Field) o;
        return this.row == other.row && this.column == other.column;
    }

}
