import negocio.ContaCorrente;
import negocio.GerenciadoraContas;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GerenciadoraContasTest {
    private GerenciadoraContas gerenciadoraContas;

    @Before
    public void setAccount() {
        ContaCorrente account1 = new ContaCorrente(1, 0, true);
        ContaCorrente account2 = new ContaCorrente(2, 0, true);

        List<ContaCorrente> accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);

        gerenciadoraContas = new GerenciadoraContas(accountList);
    }

    @Test
    public void TransferSufficientBalance() {
        gerenciadoraContas.pesquisaConta(1).setSaldo(200.0);
        gerenciadoraContas.pesquisaConta(2).setSaldo(0);
        boolean expectedTransfer = gerenciadoraContas.transfereValor(1, 100, 2);
        assertTrue(expectedTransfer);
        assertEquals(100.0, gerenciadoraContas.pesquisaConta(1).getSaldo(), 0);
        assertEquals(100.0, gerenciadoraContas.pesquisaConta(2).getSaldo(), 0);
    }

    @Test
    public void TransferInsufficientPositiveBalanceOnAccount1() {
        gerenciadoraContas.pesquisaConta(1).setSaldo(100.0);
        gerenciadoraContas.pesquisaConta(2).setSaldo(0);
        boolean expectedTransfer = gerenciadoraContas.transfereValor(1, 200, 2);
        assertTrue(expectedTransfer);
        assertEquals(-100.0, gerenciadoraContas.pesquisaConta(1).getSaldo(), 0);
        assertEquals(200.0, gerenciadoraContas.pesquisaConta(2).getSaldo(), 0);
    }

    @Test
    public void TransferInsufficientNegativeBalanceOnAccount1() {
        gerenciadoraContas.pesquisaConta(1).setSaldo(-100.0);
        gerenciadoraContas.pesquisaConta(2).setSaldo(0);
        boolean expectedTransfer = gerenciadoraContas.transfereValor(1, 200, 2);
        assertTrue(expectedTransfer);
        assertEquals(-300.0, gerenciadoraContas.pesquisaConta(1).getSaldo(), 0);
        assertEquals(200.0, gerenciadoraContas.pesquisaConta(2).getSaldo(), 0);
    }

    @Test
    public void TransferInsufficientNegativeBalanceOnSameAccount() {
        gerenciadoraContas.pesquisaConta(1).setSaldo(-100.0);
        gerenciadoraContas.pesquisaConta(2).setSaldo(-100.0);
        boolean expectedTransfer = gerenciadoraContas.transfereValor(1, 200, 2);
        assertTrue(expectedTransfer);
        assertEquals(-300.0, gerenciadoraContas.pesquisaConta(1).getSaldo(), 0);
        assertEquals(100.0, gerenciadoraContas.pesquisaConta(2).getSaldo(), 0);
    }

    @After
    public void clear() {
        gerenciadoraContas.limpa();
    }

}