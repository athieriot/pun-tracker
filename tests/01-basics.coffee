HomepageTitle = "Pun Trackr"
EmptyListMessage = "Hmmm! Nothing yet."
AddPunsId = "add-puns"
AddPunsTitle = "Add puns"

casper.start casper.cli.get("server"), () ->
  @test.assertTitle HomepageTitle, 'The application must have an homepage (title=' + HomepageTitle + ')'
  @test.assertDoesntExist 'tr', 'By default, no Puns are displayed'
  @test.assertTextExists EmptyListMessage, 'Instead, a message is present (text=' + EmptyListMessage + ')'   

  # Try to avoid using an id here
  @test.assertExists 'a#' + AddPunsId, 'A link must be available to go to the "Add Puns" page (id=' + AddPunsId + ')'
  this.click 'a#' + AddPunsId

casper.then () ->
  @test.assertTitle AddPunsTitle, 'The link leads to a new page (title=' + AddPunsTitle + ')'

  @test.assertExists 'form', 'With a form. In this form we must find:'
  @test.assertExists 'textarea', 'A textarea for the description'
  @test.assertExists 'input[type="file"]', 'A file input for the image'
  @test.assertExists 'input[type="submit"]', 'And of course, a button to submit the form'

casper.run () ->
  #@test.done 1
  @test.renderResults true
