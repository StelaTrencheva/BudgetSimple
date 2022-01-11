
describe('Create Wallet', () => {
    it('Enters wallet info and creates a wallet', () => {
        const username = "testUser";
        const password = "testUser123"
        cy.request('POST', 'http://localhost:8080/user/login', {
            username,
            password
        })

        cy.visit('http://localhost:3000/user/addWallet');
        cy.get('#title').clear();
        cy.get('#title').type('New Wallet');
        cy.get('#description').click();
        cy.get('#description').type('New Wallet');
        cy.get('#budget').clear();
        cy.get('#budget').type('100');
        cy.get('#currency').clear();
        cy.get('#currency').type('EUR');
        cy.get('.btn').click();
        cy.url().should('be.equal', 'http://localhost:3000/user/wallets')
        cy.get('h5').should('contain', 'New Wallet')
    })

})