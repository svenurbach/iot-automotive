describe('template spec', () => {
  it('Test Insurance Overview Page', () => {
    cy.visit('http://localhost:4200/insurance')
    cy.get('.entity-grid li').should('have.length.greaterThan', 0).as('contracts')
    cy.get('@contracts').first().find('.icon svg').should('be.visible')
    cy.get('@contracts').first().click()
    cy.location('href').should('include', '/contract/')
    cy.get('h1').should('contain', 'Vertragsdetails')
    cy.get('.icon').should('be.visible')
  })
})