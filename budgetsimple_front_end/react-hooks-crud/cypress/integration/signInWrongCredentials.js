
describe('Login Test Wrong Credentials', () => {
    it('Enters wrong login credentials of the user', () => {
        cy.visit('http://localhost:3000/sign-in');
        cy.get(':nth-child(2) > .form-control').clear();
        cy.get(':nth-child(2) > .form-control').type('testUser');
        cy.get(':nth-child(3) > .form-control').clear();
        cy.get(':nth-child(3) > .form-control').type('wrongPassword');
        cy.get('.btn-primary').click();
        cy.url().should('be.equal', 'http://localhost:3000/sign-in')
        cy.contains('Request failed with status code 403').should('be.visible') 
    })

})