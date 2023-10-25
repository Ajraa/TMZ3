package vsb.fei.tmz3;

import androidx.annotation.NonNull;

public class FinanceData {
    double suma;
    double vklad;
    double urok;
    double obdobi;

    public FinanceData(double suma, double vklad, double urok, double obdobi) {
        this.suma = suma;
        this.vklad = vklad;
        this.urok = urok;
        this.obdobi = obdobi;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public double getVklad() {
        return vklad;
    }

    public void setVklad(double vklad) {
        this.vklad = vklad;
    }

    public double getUrok() {
        return urok;
    }

    public void setUrok(double urok) {
        this.urok = urok;
    }

    public double getObdobi() {
        return obdobi;
    }

    public void setObdobi(double obdobi) {
        this.obdobi = obdobi;
    }

    @Override
    public String toString() {
        return "Suma: " + this.suma + ", vklad: " + this.vklad + ", Úrok: " + this.urok + ", Období: " + this.obdobi;
    }
}
