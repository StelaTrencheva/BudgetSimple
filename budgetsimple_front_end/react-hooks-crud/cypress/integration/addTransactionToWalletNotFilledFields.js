
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
        cy.get('select.form-control').select('Education');
        cy.get('form > .btn').click();
        cy.contains('Please fill all the fields!').should('be.visible')
    })

})