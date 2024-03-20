describe('template spec', () => {
  it('Test Vehicle Overview Page ',  () => {
    cy.visit('http://localhost:4200')
    cy.get('.entity-grid li').should('have.length.greaterThan', 0).as('vehicles')
    cy.get('@vehicles').first().find('.icon svg').should('be.visible')
    cy.get('@vehicles').first().click()
    cy.location('href').should('include', '/vehicle/')
    cy.get('h1').should('contain', 'Fahrzeug Details')
  })
})
