
describe('Edit User Info Test', () => {
    it('Logs in the user', () => {
        const username = "testUser";
        const password = "testUser123"
        cy.request('POST', 'http://localhost:8080/user/login', {
            username,
            password
        })

        cy.visit('http://localhost:3000/user/account');
        cy.get(':nth-child(16) > .btn').click();
        cy.get(':nth-child(2) > .col-sm-9 > input').clear();
        cy.get(':nth-child(2) > .col-sm-9 > input').type('testUserUpdated');
        cy.get(':nth-child(4) > .col-sm-9 > input').clear();
        cy.get(':nth-child(4) > .col-sm-9 > input').type('testUserUpdated');
        cy.get(':nth-child(16) > .btn').click();
        cy.get(':nth-child(16) > .btn').click();
        cy.get(':nth-child(2) > .col-sm-9 > input').clear();
        cy.get(':nth-child(2) > .col-sm-9 > input').type('testUser');
        cy.get(':nth-child(4) > .col-sm-9 > input').clear();
        cy.get(':nth-child(4) > .col-sm-9 > input').type('testUser');
        cy.get(':nth-child(16) > .btn').click();
    })

})