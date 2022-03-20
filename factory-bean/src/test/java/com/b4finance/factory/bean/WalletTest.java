package com.b4finance.factory.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WalletTest {


    @Test
    public void shouldAddAndSpend() {
        final Wallet wallet = new Wallet();
        wallet.addAmount(5);
        wallet.addAmount(10);
        wallet.spendAmount(2);
        assertThat(wallet.getTotalAmount()).isEqualTo(13L);
    }

}