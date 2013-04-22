class PunsController < ApplicationController

  def new
    @pun = Pun.new
    @title = "Add puns"
    @quote = "<p>Add a pun in your breakfast. </p><small>Anonymous</small>"

    respond_to do |format|
      format.html  # new.html.erb
    end
  end

  def create
    @pun = Pun.new(params[:pun])
    @title = "Add puns"
    @quote = "<p>Add a pun in your breakfast. </p><small>Anonymous</small>"

    respond_to do |format|
      if @pun.save
        flash[:message] = 'Your Pun is a success !'
        format.html  { redirect_to :action => "new" }
      else
        format.html  { render :action => "new" }
      end
    end
  end

  def destroy
    @pun = Pun.find(params[:id])
    @pun.destroy

    respond_to do |format|
      format.html { redirect_to :controller => "application", :action => "index" }
    end
  end
end
