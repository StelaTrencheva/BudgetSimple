
describe('Sign up Existing User Test', () => {
    it('Enters existing user information and gets an account', () => {
        cy.visit('http://localhost:3000/sign-up');
        cy.get(':nth-child(3) > .form-control').clear();
        cy.get(':nth-child(3) > .form-control').type('testUser');
        cy.get(':nth-child(4) > .form-control').clear();
        cy.get(':nth-child(4) > .form-control').type('testUser');
        cy.get(':nth-child(5) > .form-control').clear();
        cy.get(':nth-child(5) > .form-control').type('testUser@abv.bg');
        cy.get(':nth-child(6) > .form-control').clear();
        cy.get(':nth-child(6) > .form-control').type('testUser');
        cy.get(':nth-child(7) > .form-control').clear();
        cy.get(':nth-child(7) > .form-control').type('0123456789');
        cy.get(':nth-child(8) > .form-control').clear();
        cy.get(':nth-child(8) > .form-control').type('testUser');
        cy.get(':nth-child(9) > .form-control').clear();
        cy.get(':nth-child(9) > .form-control').type('testUser123');
        cy.get(':nth-child(10) > .form-control').clear();
        cy.get(':nth-child(10) > .form-control').type('2021-01-01');
        cy.get('.btn-primary').click();
        cy.url().should('be.equal', 'http://localhost:3000/sign-up')
        cy.contains('Request failed with status code 400').should('be.visible') 
    })

})