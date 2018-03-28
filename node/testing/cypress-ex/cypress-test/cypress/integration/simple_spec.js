// describe('My First Test', function() {
//     it('Does not do much!', function() {
//         expect(true).to.equal(true);
//     });
// });

// describe('My First Test', function() {
//     it('Does not do much!', function() {
//         expect(true).to.equal(true);
//     });
// });

// describe('My First Test', function() {
//     it('Visits the Kitchen Sink', function() {
//         cy.visit('https://example.cypress.io')
//         cy.contains('type').click();
//         cy.url().should('include', '/commands/actions');
//         cy.get('.action-email')
//             .type('fake@email.com')
//             .should('have.value', 'fake@email.com') ;
//     });
// });


// describe('Another test', function() {
//     it('Does not do much!', function() {
//         expect(true).to.equal(true);
//     });
// });


describe('google', function() {
    it('Visits the Kitchen Sink', function() {
        cy.visit('https://google.com');
        cy.get('#lst-ib').type('stuff');
        cy.get('#lst-ib').type('{esc}');
        cy.get('center > [value="Google Search"]').click();

        // cy.contains('cy.get('body')')
        // cy.pause();
        // cy.contains('type').click();
        // cy.url().should('include', '/commands/actions');
        // cy.get('.action-email')
        //     .type('fake@email.com')
        //     .should('have.value', 'fake@email.com') ;
    });
});
