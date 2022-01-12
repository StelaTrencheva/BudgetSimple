
describe('Change Wallet Budget', () => {
    it('Enters wallet budget value and updates the budget', () => {
        const username = "testUser";
        const password = "testUser123"
        cy.request('POST', 'http://localhost:8080/user/login', {
            username,
            password
        })

        cy.visit('http://localhost:3000/user/wallets');
        cy.get(':nth-child(1) > .card-body > .row > :nth-child(1) > a > .smallLogo').click();
        cy.wait(1000);
        cy.get(':nth-child(2) > .btn').click();
        cy.wait(1000);
        cy.get('#newBudget').clear();
        cy.get('#newBudget').type('10');
        cy.get('form > .row > :nth-child(2) > .btn').click();
        cy.wait(1000);
        cy.get(':nth-child(2) > .btn').click();
        cy.wait(1000);
        cy.get(':nth-child(3) > :nth-child(2) > p').should('have.text', '10');
    })

})