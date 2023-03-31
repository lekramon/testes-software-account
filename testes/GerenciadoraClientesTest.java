import negocio.Cliente;
import negocio.GerenciadoraClientes;
import negocio.IdadeNaoPermitidaException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GerenciadoraClientesTest {

    private GerenciadoraClientes gerenciadoraClientes;

    @Before
    public void setClientById() {
        Cliente client1 = new Cliente(1, "Carlos", 25, "carlos@uol.com", 3, true);
        Cliente client2 = new Cliente(2, "Alexandre", 18, "ale@pag.com", 2, true);

        List<Cliente> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);

        gerenciadoraClientes = new GerenciadoraClientes(clientList);
    }

    @Test
    public void shouldSearchClient() {
        Cliente actualClient = gerenciadoraClientes.pesquisaCliente(1);
        Cliente expectedClient = new Cliente(1, "Carlos", 25, "carlos@uol.com", 3, true);
        assertEquals(expectedClient, actualClient);
    }

    @Test
    public void shouldRemoveClient() {
        boolean expectedResult = gerenciadoraClientes.removeCliente(2);
        assertTrue(expectedResult);
        assertNull(gerenciadoraClientes.pesquisaCliente(2));
        assertEquals(1, gerenciadoraClientes.getClientesDoBanco().size());
    }

    @Test
    public void shouldPermitAge() throws IdadeNaoPermitidaException {
        assertTrue(gerenciadoraClientes.validaIdade(18));
        assertTrue(gerenciadoraClientes.validaIdade(65));
    }

    @Test(expected = IdadeNaoPermitidaException.class)
    public void shouldLimitAge() throws IdadeNaoPermitidaException {
        gerenciadoraClientes.validaIdade(17);
        gerenciadoraClientes.validaIdade(66);
    }


    @After
    public void clear() {
        gerenciadoraClientes.limpa();
    }


}
