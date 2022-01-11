
describe('Add Transaction to Wallet', () => {
    it('Enters transaction info and creates a transaction', () => {
        const username = "testUser";
        const password = "testUser123"
        cy.request('POST', 'http://localhost:8080/user/login', {
            username,
            password
        })

        cy.visit('http://localhost:3000/user/wallets');
        cy.get(':nth-child(1) > .card-body > .row > :nth-child(1) > a > .smallLogo').click();
        cy.get(':nth-child(1) > .btn').click();
        cy.get('.col-lg-12 > .btn').click();
        cy.get('#title').clear();
        cy.get('#title').type('new Transaction');
        cy.get('#description').click();
        cy.get('#description').type('new Transaction');
        cy.get('#amount').clear();
        cy.get('#amount').type('15');
        cy.get('select.form-control').select('Education');
        cy.get('#split_amount_testUser').clear();
        cy.get('#split_amount_testUser').type('15');
        cy.get('form > .btn').click();
        cy.wait(1000);
        cy.get('#transactions').click();
        cy.get('h5').should('contain', 'new Transaction')
    })

})