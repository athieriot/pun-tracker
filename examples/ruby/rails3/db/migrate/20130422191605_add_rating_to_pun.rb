class AddRatingToPun < ActiveRecord::Migration
  def change
    add_column :puns, :rating, :integer, :default => 0
  end
end
