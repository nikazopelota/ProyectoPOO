import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Avance01Test {

    @Test
    public void testLimpiarMatricula() {
        // Valores de entrada y salida esperada
        String input = "21.212.121-k";
        String expectedOutput = "21212121k";

        // Llamada al método a probar
        String actualOutput = Avance01.limpiarMatricula(input);

        // Verificar que el resultado es el esperado
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testLimpiarMatriculaSoloNumeros() {
        // Valores de entrada y salida esperada
        String input = "2121212121";
        String expectedOutput = "2121212121";

        // Llamada al método a probar
        String actualOutput = Avance01.limpiarMatricula(input);

        // Verificar que el resultado es el esperado
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testLimpiarMatriculaConCaracteresInvalidos() {
        // Valores de entrada y salida esperada
        String input = "21!@212#12-k";
        String expectedOutput = "2121212k";

        // Llamada al método a probar
        String actualOutput = Avance01.limpiarMatricula(input);

        // Verificar que el resultado es el esperado
        assertEquals(expectedOutput, actualOutput);
    }
}
