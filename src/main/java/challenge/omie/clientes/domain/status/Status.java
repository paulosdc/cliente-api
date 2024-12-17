package challenge.omie.clientes.domain.status;

public enum Status {
    DESATIVADO(0),
    ATIVADO(1),
    SUSPENSO(2);

    private final int codigo;

    Status(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}