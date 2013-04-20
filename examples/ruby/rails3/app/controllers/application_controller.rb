class ApplicationController < ActionController::Base
  protect_from_forgery

  def index
    @puns = Pun.all
    @title = "Pun Trackr"
    @quote = "<p>Pun Trackr will revolutionize the pun-guin industry. <span class=\"muted small\">(not)</span></p>"

    respond_to do |format|
      format.html  # index.html.erb
    end
  end
end
