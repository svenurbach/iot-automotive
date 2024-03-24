describe('template spec', () => {
  it('Test Vehicle Details Page ',  () => {
    cy.visit('http://localhost:4200/vehicle/1')
    cy.location('href').should('include', '/vehicle/')
    cy.get('h1').should('contain', 'Fahrzeugdetails')

    cy.get('.vehicle-img').should('be.visible');

    cy.get('.vehicle-details').should('have.length', 5);
    cy.get('.vehicle-details').eq(0).should('contain', 'Hersteller:');
    cy.get('.vehicle-details').eq(1).should('contain', 'Modell:');
    cy.get('.vehicle-details').eq(2).should('contain', 'Kennzeichen:');
    cy.get('.vehicle-details').eq(3).should('contain', 'Baujahr:');
    cy.get('.vehicle-details').eq(4).should('contain', 'Fahrten:');
    cy.get('p').should('be.visible');
    cy.get('.osm-view-container').should('be.visible')

    cy.get('.loc-pieChart').should('exist');
    cy.get('#pieChartErrors').should('exist');
    cy.get('.chart-container canvas').should('exist');
  })
  })
