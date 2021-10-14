package dad.rubenpablo.cambiodivisa;

public enum Divisa {
	Euro("Euro", 1.0), Yen("Yen", 133.59), Dolar("Dólar", 1.2007), Libra("Libra", 0.8873);

	Divisa(String nombre, Double tasa) {
		this.nombre = nombre;
		this.tasa = tasa;
	}

	private final String nombre;
	private final Double tasa;

	public String getNombre() {
		return nombre;
	}

	public Double getTasa() {
		return tasa;
	}

	public Double fromEuro(Double euros) {
		return euros * tasa;
	}

	public Double toEuro(Double moneda) {
		return moneda / tasa;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public static Double fromTo(Divisa from, Divisa to, Double cantidad) {
		return to.fromEuro(from.toEuro(cantidad));
	}
}
