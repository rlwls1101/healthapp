package com.example.capston_project;


public class Matrix {
    public static final int NOT_FOUNT = -2147483646;
    private double[][] matrix;
    private int row, col;

    /**
     * 0행렬 만들기
     *
     * @param row
     *            행 크기
     * @param col
     *            열 크기
     * @return
     */
    public static Matrix zeros(int row, int col) {
        Matrix temp = new Matrix(row, col);
        temp.setZeroMatrix();
        return temp;
    }

    /**
     * i * i 크기의 0행렬
     *
     * @param i
     * @return
     */
    public static Matrix zeros(int i) {
        return Matrix.zeros(i, i);
    }

    /**
     * 1행렬 만들기
     *
     * @param row
     *            행 크기
     * @param col
     *            열 크기
     * @return
     */
    public static Matrix ones(int row, int col) {
        Matrix temp = new Matrix(row, col);
        temp.setOnesMatrix();
        return temp;
    }

    /**
     * i * i 크기의 1행렬
     *
     * @param i
     * @return
     */
    public static Matrix ones(int i) {
        return Matrix.ones(i, i);
    }

    /**
     * i*i의 단위행렬
     *
     * @param i
     * @return
     */
    public static Matrix unitMatrix(int i) {
        Matrix temp = new Matrix(i);
        temp.setUnitMatrix();
        return temp;
    }

    /**
     * 행렬식을 계산한다.
     * determinant()을 사용할것
     *
     * @param target
     * @return target의 행렬식
     */
    @Deprecated
    public static double determinant1(Matrix target) {
        // 라이프니츠 공식에 의한 행렬식 계산
        double det = 0;
        if (target.getRowSize() == target.getColSize()) {
            double[][] A = target.getMatrix();
            if (A.length == 1) {
                return A[0][0];
            } else {
                for (int i = 0; i < A[0].length; i++) {
                    det += A[0][i] * target.cofactor(0, i);// 재귀
                    // 호출
                }
            }
            return det;
        } else {
            System.out.println("행렬식을 구할수 없습니다.");
            return NOT_FOUNT;
        }

    }

    /**
     * 가우스 소거법에 의한 행렬식 계산
     *
     * @param target
     * @return
     */
    public static double determinant(Matrix target) {
        if (target.getRowSize() == target.getColSize()) {
            double[][] temp = new double[target.row][target.row];
            // temp=getMatrix()로 하면 레퍼런스로 되어서 본래 행렬이
            // 변경됨
            for (int i = 0; i < target.row; i++) {
                for (int j = 0; j < target.row; j++) {
                    temp[i][j] = target.matrix[i][j];
                }
            }
            for (int n = 0; n < temp.length; n++) {
                if (temp[n][n] == 0) {
                    for (int a = n; a < temp.length; a++) {
                        if (temp[a][n] != 0) {
                            for (int b = n; b < temp.length; b++) {
                                temp[n][b] += temp[a][b];
                            }
                            break;
                        }
                    }
                    if (temp[n][n] == 0) {
                        System.out.println(n + "ss");
                        return 0;
                    }
                }
                for (int i = n + 1; i < temp.length; i++) {
                    {
                        for (int j = temp[0].length - 1; j >= n; j--) {
                            temp[i][j] = temp[i][j] - temp[i][n] * temp[n][j]
                                    / temp[n][n];// 수정필요
                        }
                    }
                }
                // new Matrix(temp).displayMatix();
                // //test
                // System.out.println("aaa");
            }
            double sum = 1;
            for (int i = 0; i < temp.length; i++) {
                sum *= temp[i][i];
            }
            // new Matrix(temp).displayMatix();
            return sum;
        } else {
            System.out.println("행렬식을 구할수 없습니다.");
            return NOT_FOUNT;
        }
    }

    /**
     * 여행렬 구하기
     *
     * @param target
     * @param row
     * @param col
     * @return target의 row 행과 col 열을 제외한 여행렬
     */
    public static double cofactor(Matrix target, int row, int col) {
        Matrix temp = target.minor(row, col);

        return temp.determinant() * Math.pow((-1), (row + col));
    }

    /**
     * 소행렬 구하기
     *
     * @param target
     * @param removeRow
     * @param removeCol
     * @return target의 row 행과 col 열을 제외한 소행렬
     */
    public Matrix minor(int removeRow, int removeCol) {
        int ar = 0, ac = 0;
        double temp[][] = new double[row - 1][col - 1];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (!(r == removeRow || c == removeCol)) {
                    temp[ar][ac] = matrix[r][c];
                    if (++ac >= col - 1) {
                        if (++ar < row - 1)
                            ac = 0;
                    }
                }
            }
        }
        return new Matrix(temp);
    }

    /**
     * 행렬의 합
     *
     * @param A
     * @param B
     * @return
     */
    public static Matrix plus(Matrix A, Matrix B) {
        double[][] temp = Matrix.zeros(A.row, A.col).getMatrix();
        double[][] tempA = A.getMatrix();
        double[][] tempB = B.getMatrix();
        if (equalSize(A, B)) {
            for (int i = 0; i < tempA.length; i++) {
                for (int k = 0; k < tempA[0].length; k++) {
                    temp[i][k] = tempA[i][k] + tempB[i][k];
                }
            }
            return new Matrix(temp);
        }
        return new Matrix(temp);
    }

    /**
     * 행렬의 차
     *
     * @param A
     * @param B
     * @return
     */
    public static Matrix minus(Matrix A, Matrix B) {
        double[][] temp = Matrix.zeros(A.row, A.col).getMatrix();
        double[][] tempA = A.getMatrix();
        double[][] tempB = B.getMatrix();
        if (equalSize(A, B)) {
            for (int i = 0; i < tempA.length; i++) {
                for (int k = 0; k < tempA[0].length; k++) {
                    temp[i][k] = tempA[i][k] - tempB[i][k];
                }
            }
            return new Matrix(temp);
        }
        return new Matrix(temp);
    }

    /**
     * 행렬의 모양 비교
     *
     * @param A
     * @param B
     * @return
     */
    public static boolean equalSize(Matrix A, Matrix B) {
        if (A.getRowSize() == B.getRowSize()
                && A.getColSize() == B.getColSize()) {
            return true;
        } else {
            return false;
        }
    }

    Matrix(int i) // 행,열 의 크기가 같은 생성자
    {
        this.row = i;
        this.col = i;
        matrix = new double[i][i];
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < i; b++) {
                matrix[a][b] = (int) (Math.random() * 10 * Math.pow(-1,
                        (int) Math.random() * 10));
            }
        }
    }

    Matrix(int row, int col) // 행 열의 크기가 다른 생성자
    {
        this.row = row;
        this.col = col;
        matrix = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                matrix[i][k] = (int) (Math.random() * 10 * Math.pow(-1,
                        (int) Math.random() * 10));
            }
        }
    }

    public Matrix(double[][] temp) // 임의의 행렬을 입력받는 생성
    {
        this.matrix = temp;
        row = temp.length;
        col = temp[0].length;
    }

    /**
     *
     * @return 행렬 원소중 Not a Number 인 원소가 있다면 true
     */
    public boolean isNaN() {
        for (int i = 0; i < col; i++)
            for (int j = 0; j < row; j++)
                if (Double.isNaN(matrix[j][i]))
                    return false;
        return true;
    }

    /**
     * 자기 자신을 영행렬로 바꾸기
     */
    public void setZeroMatrix() {
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                matrix[i][k] = 0;
            }
        }
    }

    /**
     * 자기 자신을 모두 1인 행렬로 바꿈
     */
    public void setOnesMatrix() {
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                matrix[i][k] = 1;
            }
        }
    }

    /**
     * 자기자신을 단위행렬로
     */
    public void setUnitMatrix() {
        setZeroMatrix();
        for (int i = 0; i < row; i++) {
            matrix[i][i] = 1;
        }
    }

    public void displayMatix() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                if (matrix[i][k] == -0) {
                    // 이 부분이 없으면 -0과 0이 동시에 나오게 된다
                    matrix[i][k] = 0;
                }
                temp += Double.toString(matrix[i][k]);
                // C 의 printf와 유사
                if (k != col - 1) {
                    temp += ",  ";
                }
            }
            temp += System.getProperty("line.separator");
        }
        return temp;
    }

    /**
     * 여인수
     *
     * @param row
     * @param col
     * @return
     */
    public double cofactor(int row, int col) {
        return minor(row, col).determinant() * Math.pow((-1), row + col);
    }

    /**
     * @param temp
     *            행렬의 값을 temp로 바꿈
     */
    public void setMatrix(double temp[][]) {
        // 행렬 내용 수정 temp와 matrix가 다를시 문제가 생길수 있음
        // length로 행과 열의 길이 파악
        this.row = temp.length;
        this.col = temp[0].length;
        this.matrix = temp;
    }

    /**
     * 전치행렬
     *
     * @return 이 행렬의 전치행렬
     */
    public Matrix transpose() {
        Matrix tempM = new Matrix(col, row);
        double temp[][] = new double[col][row];
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                temp[k][i] = matrix[i][k];
            }
        }
        tempM.setMatrix(temp);
        return tempM;
    }

    /**
     * 행렬의 상수배
     *
     * @param m
     *            행렬에 곱할 수
     * @return 현재 행렬의 m 배된 행렬
     */
    public Matrix multiply(double m) {
        Matrix temp = new Matrix(row, col);
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                temp.matrix[i][k] = this.matrix[i][k] * m;
            }
        }
        return temp;
    }

    /**
     * 행렬의 곱
     *
     * @param secMatrix
     *            현재 행렬에 곱해질 행렬
     * @return 현재 행렬X secMatrix
     */
    public Matrix multiply(Matrix secMatrix) // 행렬*행렬
    {
        // Matrix temp;
        double[][] temp;
        // 곱하기 위해 앞쪽 행렬의 열과 뒷쪽행렬의 행이 같아야함
        if (this.col == secMatrix.row) {
            temp = Matrix.zeros(this.row, secMatrix.col).getMatrix();
            double[][] A = this.getMatrix();
            double[][] B = secMatrix.getMatrix();
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < secMatrix.col; j++) {
                    for (int a = 0; a < this.col; a++) {
                        // System.out.println("i="+i+" k="+k+" a="+a);
                        // //테스트
                        temp[i][j] += A[i][a] * B[a][j];
                    }
                }
            }
            return new Matrix(temp);
        } else {
            System.out.println("계산할 수 없습니다.");
            return Matrix.zeros(1);
        }

    }

    /**
     * 역행렬
     * invers()를 사용할것
     *
     * @return 현재 행렬의 역행렬
     */
    @Deprecated
    public Matrix invers1() {
        // 역행렬// 행렬의 크기가 8x8이 넘어가면 출력하는데 시간이 너무 오래걸린다.
        // 알고리즘의 최적화가 필요한듯 하다. ->invers() 알고리즘 변경
        Matrix temp = null;
        if (row == col) // 행==열인 행렬인지 확인
        {
            temp = Matrix.zeros(row);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < row; j++) {
                    temp.matrix[i][j] = cofactor(i, j);
                    // i행j열의 값 =여인수(i,j)
                }

            }
            temp = temp.transpose();// 전치
            double det = determinant1(this);
            // 원래 행렬의 행렬식계산
            if (det != 0) {
                return temp.multiply(1.0 / det);
                // 마지막으로 행렬식으로 나눠준다.
            } else {
                System.out.println("역행렬이 없습니다.");
                // 행렬식==0이면 역행렬이 없다.
                return Matrix.zeros(1);
            }

        } else {
            System.out.println("행과 열의 크기가 같지 않습니다.");
            return Matrix.zeros(1);
        }
    }

    /**
     * 역행렬<br>



     * 가우스 조던 소거법
     * invers1에 비하여 매우 빠르다 invers 는 9차 이상의 행렬에서
     * 동작속도를 보장할수 없다.
     * 하지만 이것은 100차 행렬이 넘어가도 빠른속도로 계산이 가능하다.
     * 하지만 역행렬이 존재 하지 않는 행렬에서도 답이 나오므로 선행해서 걸러낼 필요가
     * 있다.
     * 마지막에 원래 행렬과 만들어진 역행렬을 곱해서 단위행렬이 나오는지 검사
     *
     * @return 현재 행렬의 역행렬. 역행렬을 만들수 없는 경우에는 null
     */
    public Matrix invers() {

        double[][] temp = new double[row][row];
        // temp=this.getMatrix()로 하면 레퍼런스로 되어서 본래 행렬이
        // 변경됨
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                temp[i][j] = matrix[i][j];
            }
        }
        double[][] comp = Matrix.unitMatrix(col).getMatrix();
        for (int n = 0; n < col; n++) {
            if (temp[n][n] == 0) //
            {
                for (int k = n; k < col; k++) {
                    if (temp[k][n] != 0) {
                        for (int a = 0; a < col; a++) {
                            comp[n][a] = comp[n][a] + comp[k][a];
                            temp[n][a] = temp[n][a] + temp[k][a];
                        }
                        break;
                    }
                }
            }
            double p = temp[n][n];
            for (int a = 0; a < col; a++) {
                comp[n][a] /= p;
                temp[n][a] /= p;
            }
            for (int i = 0; i < col; i++) {
                if (i != n) {
                    double p1 = temp[i][n];
                    for (int j = col - 1; j >= 0; j--) {
                        comp[i][j] -= p1 * comp[n][j];
                        temp[i][j] -= p1 * temp[n][j];
                    }
                }
            }

        }
        Matrix tempReturn = new Matrix(comp);
        if (multiply(tempReturn).equals(Matrix.unitMatrix(row))) {
            // 만들어진 결과가 역행렬이 맞는지 확인
            return tempReturn;
        } else { // 아닐경우 null리턴
            System.out.println("경고)역행렬이 존재하않는 행렬일수 있습니다.");
            return null;
        }

    }

    /**
     * 왼쪽 나눗셈<br>



     * 선형방정식의 해를 구할때 씀 this*X=temp, X=this'*temp 일때
     * X를 구할수있다.
     *
     * @param temp
     * @return
     */
    public Matrix leftDivision(Matrix temp) {

        return this.invers().multiply(temp);
    }

    /**
     * 수반행렬
     *
     * @return
     */
    public Matrix adjoint() {
        double temp[][] = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                temp[i][j] = cofactor(i, j);
            }
        }
        return new Matrix(temp);
    }

    /**
     * 행렬식
     *
     * @return
     */
    public double determinant() // 행렬식
    {
        return Matrix.determinant(this);
    }

    public String size()
    // 전채 크기를 String로 리턴 배열로 리턴하려했으나 조금 문제가 있음
    {
        return row + " " + col;
    }

    /**
     *
     * @return Matrix의 배열을 리턴
     */
    public double[][] getMatrix() {
        return matrix;
    }

    /**
     *
     * @return 행의 크기
     */
    public int getRowSize() {
        return this.row;
    }

    /**
     *
     * @return 열의 크기
     */
    public int getColSize() {
        return col;
    }

    /**
     * 행렬의 덧셈
     *
     * @param A
     *            더해질 행렬
     * @return 현재 행렬에 A 를 더한 행렬
     */

    public Matrix plus(Matrix A) {

        double[][] temp = Matrix.zeros(A.row, A.col).getMatrix();
        if (equalSize(A, this)) {
            for (int i = 0; i < A.getRowSize(); i++) {
                for (int k = 0; k < A.getColSize(); k++) {
                    temp[i][k] = A.matrix[i][k] + this.matrix[i][k];
                }
            }
            return new Matrix(temp);
        }
        return new Matrix(temp);
    }

    /**
     * 행렬의 뺄셈
     *
     * @param A
     *            더해질 뺄셈
     * @return 현재 행렬에 A 를 뺀 행렬
     */
    public Matrix minus(Matrix A) {
        // 행렬 뺄셈
        double[][] temp = Matrix.zeros(A.row, A.col).getMatrix();
        if (equalSize(A, this)) {
            for (int i = 0; i < A.getRowSize(); i++) {
                for (int k = 0; k < A.getColSize(); k++) {
                    temp[i][k] = A.matrix[i][k] - this.matrix[i][k];
                }
            }
            return new Matrix(temp);
        }
        return new Matrix(temp);
    }

    /**
     *
     * @param m
     *            비교할 행렬
     * @return 현재 행렬과 m이 같으면 true
     */
    public boolean equals(Matrix m) {
        if (m.row == this.row && m.col == this.col) {
            for (int i = 1; i < row; i++) {
                for (int j = 1; j < col; j++) {
                    if (Math.abs(m.matrix[i][j] - this.matrix[i][j]) >= 0.0000001) {
                        // 컴퓨터로 계산한 값이기 때문에 두 값의 차가
                        // 0.0000001 보다 작으면 같다고 볼수 있다.
                        // 행렬의 원소 자체가 매우 작으면 문제가 발생할수
                        // 있으나 이경우는 무시하기로한다
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public double gettotal() {
        double total=0;
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                total+=matrix[row][column];
            }
        }
        return total;
    }

    public double[] rowtotal(){
        double []rowtotal=new double[row];
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                rowtotal[row]+=matrix[row][column];
            }
        }
        return rowtotal;
    }
}