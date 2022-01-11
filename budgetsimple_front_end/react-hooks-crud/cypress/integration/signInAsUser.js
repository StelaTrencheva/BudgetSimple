
describe('Login Test', () => {
    it('Enters login credentials of the user', () => {
        cy.visit('http://localhost:3000/sign-in');
        cy.get(':nth-child(2) > .form-control').clear();
        cy.get(':nth-child(2) > .form-control').type('testUser');
        cy.get(':nth-child(3) > .form-control').clear();
        cy.get(':nth-child(3) > .form-control').type('testUser123');
        cy.get('.btn-primary').click();
        cy.url().should('be.equal', 'http://localhost:3000/user/account');
        cy.getCookie('auth').should('exist');
        cy.get('h4').should('contain', 'testUser testUser');
        cy.get('.ml-auto > [href="/user/rateUs"]').should('contain','Rate us');
    })

})