
describe('Create Wallet not fill all fields', () => {
    it('Enters wallet on some fields, does not create a wallet', () => {
        const username = "testUser";
        const password = "testUser123"
        cy.request('POST', 'http://localhost:8080/user/login', {
            username,
            password
        })

        cy.visit('http://localhost:3000/user/addWallet');
        cy.get('#title').clear();
        cy.get('#title').type('New Wallet');
        cy.get('#budget').clear();
        cy.get('#budget').type('100');
        cy.get('.btn').click();
        cy.url().should('be.equal', 'http://localhost:3000/user/addWallet')
        cy.contains('Please fill all the fields!').should('be.visible')
    })

})
