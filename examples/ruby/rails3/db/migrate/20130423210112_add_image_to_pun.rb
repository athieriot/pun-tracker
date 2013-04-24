class AddImageToPun < ActiveRecord::Migration
  def change
    add_column :puns, :image, :string
  end
end
